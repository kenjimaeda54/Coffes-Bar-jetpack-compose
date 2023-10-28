package com.example.coffesbarcompose.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffesbarcompose.data.DataOrException
import com.example.coffesbarcompose.models.AvatarModel
import com.example.coffesbarcompose.models.UpdateAvatarModel
import com.example.coffesbarcompose.models.UserCacheViewModel
import com.example.coffesbarcompose.models.UserModel
import com.example.coffesbarcompose.repository.CoffeesBarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AvatarViewModel @Inject constructor(
    private val coffeesBarRepository: CoffeesBarRepository,
    private val userCacheViewModel: UserCacheViewModel
) : ViewModel() {
    var userAvatar: MutableState<DataOrException<AvatarModel, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )
    var dataAvatars: MutableState<DataOrException<List<AvatarModel>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )

    init {
        if (userCacheViewModel.getUser() != null) {
            getOnlyAvatar()
        }
    }

    fun getAllAvatars() {
        viewModelScope.launch {
            dataAvatars.value.isLoading = true

            dataAvatars.value = coffeesBarRepository.getAvatars()

            if (dataAvatars.toString().isNotEmpty()) {
                dataAvatars.value.isLoading = false
            }

        }
    }


    private fun getOnlyAvatar() {
        viewModelScope.launch {
            userAvatar.value.isLoading = true
            val user = userCacheViewModel.getUser()

            if (user != null) {
                userAvatar.value =
                    user.let { coffeesBarRepository.getOnlyAvatar(user.avatarId) }
            }
            if (userAvatar.toString().isNotEmpty()) {
                userAvatar.value.isLoading = false
                userAvatar.value.data = userAvatar.value.data
            }
        }
    }

    fun updateAvatarUser(id: String, updateAvatarModel: UpdateAvatarModel) {
        viewModelScope.launch {
            val response = coffeesBarRepository.updateAvatarUser(id, updateAvatarModel)
            val dataAvatar = coffeesBarRepository.getOnlyAvatar(updateAvatarModel.avatarId)
            val user = userCacheViewModel.getUser()
            if (response.data != null && response.data == true) {
                val newUser = user?.let {
                    UserModel(
                        _id = user._id,
                        name = user.name,
                        email = it.email,
                        password = user.password,
                        avatarId = updateAvatarModel.avatarId
                    )
                }
                if (newUser != null) {
                    userCacheViewModel.saveUser(newUser)
                    userAvatar.value.data = dataAvatar.data
                }
            }
        }
    }
}