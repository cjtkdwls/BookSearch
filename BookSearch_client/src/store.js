import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import api from './api'

Vue.use(Vuex)

export default new Vuex.Store({
  getters: {
    // User 정보 Getter
    getUserInfo(state) {
      return { userId: state.userId, userPassword: state.authToken }
    },
    // Book 검색 정보 Getter
    getBookList(state) {
      return state.searchedBooks
    },
    // History 검색기록 Getter
    getHistory(state) {
      return state.searchHistory
    },
    // Keyword Best 10 Getter
    getKeyword(state) {
      return state.bestBooks
    }
  },
  state: {
    // 공통 정보
    error: false,
    message: null,

    // User 정보 관련
    userId: "",
    authToken: "",

    // Book Data 관련
    searchedBooks: {
      keyword: "",
      page: 0,
      size: 0,
      total: 0,
      bookList: []
    },

    searchHistory: [],

    bestBooks: [],
  },

  mutations: {
    // User 정보 관련
    SET_USER_INFO (state, data) {
      state.userId = data.userId
      state.authToken = data.token
    },
    DELETE_USER_INFO (state) {
      state.userId = ''
      state.authToken = ''
    },

    // Book Data 관련
    SET_SEARCH_RESULT_BOOKS (state, data) {
      state.searchedBooks.keyword = data.keyword
      state.searchedBooks.page = data.page
      state.searchedBooks.size = data.size
      state.searchedBooks.total = data.total
      state.searchedBooks.bookList = data.bookList
    },
    SET_SEARCH_RESULT_BEST (state, data) {
      state.bestBooks = data
    },
    SET_SEARCH_RESULT_HISTORY (state, data) {
      state.searchHistory = data
    }

  },
  actions: {
    // User 정보 관련
    login({ commit }, data) {
      return axios.post(`/api/user/login?userId=${data.userId}&userPassword=${data.userPassword}`)
    },
    register({ commit }, data) {
      return axios.post(`/api/user/register?userId=${data.userId}&userPassword=${data.userPassword}`)
    },
    logout({ commit }) {
      commit('DELETE_USER_INFO')
      api.removeHeader()
    },


    // Book Data 관련
    bookSearch({ commit }, data) {
      if(!data.page) { return axios.get(`/api/book/search?query=${data.query}`) }
      else { return axios.get(`/api/book/search?query=${data.query}&page=${data.page}&size=${data.size}`) }
    },
    searchHistory({ commit }) {
      axios.get(`/api/book/history`).then(res => {
        commit('SET_SEARCH_RESULT_HISTORY', res.data)
      })
    },
    searchBestKeyword({ commit }) {
      axios.get(`/api/book/keyword`).then(res => {
        commit('SET_SEARCH_RESULT_BEST', res.data)
      })
    }
  }
})
