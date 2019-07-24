import axios from 'axios'
import { CookieStorage } from "cookie-storage"

const cookieStorage = new CookieStorage()

const api = {
    init(baseURL) {
        if(cookieStorage.getItem('jwtToken')) {
            axios.defaults.headers.common["X-Auth-Token"] = cookieStorage.getItem('jwtToken')
        }
        axios.defaults.baseURL = baseURL
        axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*'
        axios.defaults.headers.common['Access-Control-Allow-Methods'] = '*'
        axios.defaults.headers.common['Access-Control-Allow-Headers'] = '*'
    },

    setHeader(token) {
        cookieStorage.setItem('jwtToken', token, {path: '/'})
        axios.defaults.headers.common["X-Auth-Token"] = token
    },

    removeHeader() {
        cookieStorage.removeItem('jwtToken')
        axios.defaults.headers.common = {}
    }
}

export default api
