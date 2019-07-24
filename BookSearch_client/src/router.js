import Vue from 'vue'
import Router from 'vue-router'

import Home from './views/Home.vue'
import History from './views/History'
import Login from './views/Login'
import Register from './views/Register'

import User from './components/layouts/User'
import Main from './components/layouts/Main'

import { CookieStorage } from "cookie-storage"


Vue.use(Router)

const cookieStorage = new CookieStorage()

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: Main,
      children: [
        {
          path: '',
          name: 'Search Home',
          component: Home
        },
        {
          path : 'history',
          name: 'Search history',
          component: History
        }
      ],
      meta: {
        authenticated: true
      }
    },
    {
      path: '/user',
      component: User,
      children: [
        {
          path: 'login',
          name: 'User Login',
          component: Login
        },
        {
          path: 'register',
          name: 'User Register',
          component: Register
        }
      ],
      meta: {
        authenticated: false
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  const loggedIn = cookieStorage.getItem('jwtToken')

  if (!loggedIn && !to.path.includes('user')) {
    return next({
      path: '/user/login'
    })
  }
  if (loggedIn && to.path.includes('user')) {
    return next({
      path: '/'
    })
  }

  next()
})

export default router
