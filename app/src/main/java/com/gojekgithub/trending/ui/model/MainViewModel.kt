package com.gojekgithub.trending.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gojekgithub.trending.constants.FilterResponse
import com.gojekgithub.trending.constants.NetworkResponse
import com.gojekgithub.trending.constants.Resource
import com.gojekgithub.trending.constants.Status
import com.gojekgithub.trending.data.model.GitRepositoryModel
import com.gojekgithub.trending.data.repo.TrendingRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: TrendingRepository
) : ViewModel() {
    private val gitRepos = MutableLiveData<Resource<List<GitRepositoryModel>>>()
    val repos: LiveData<Resource<List<GitRepositoryModel>>>
        get() = gitRepos

    init {
        fetchGitRepos()
    }

    fun sortData(itemId: Int) {
        if (gitRepos.value == null) {
            return
        }
        if (gitRepos.value!!.status != Status.Success) {
            return
        }
        viewModelScope.launch {
            mainRepository.filterRepos(itemId, gitRepos.value!!.data).onStart {
                gitRepos.postValue(Resource.loading(null))
            }.catch { e -> gitRepos.postValue(Resource.error(e.toString(), null))
            }.collect {
                when (it) {
                    is FilterResponse.Success -> gitRepos.postValue(Resource.success(it.data))
                    is FilterResponse.Error -> gitRepos.postValue(
                        Resource.error(
                            it.data.toString(),
                            null
                        )
                    )
                }
            }
        }
    }

    fun fetchGitRepos(forcedRemote: Boolean = false) {
        viewModelScope.launch {
            mainRepository.getRepositories(forcedRemote).onStart {
                gitRepos.postValue(Resource.loading(null))
            }.catch { e -> gitRepos.postValue(Resource.error(e.toString(), null))
            }.collect {
                when (it) {
                    is NetworkResponse.Success -> gitRepos.postValue(Resource.success(it.data))
                    is NetworkResponse.Error -> gitRepos.postValue(
                        Resource.error(
                            it.data.toString(),
                            null
                        )
                    )
                }
            }
        }
    }

    companion object
}