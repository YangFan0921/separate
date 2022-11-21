import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/store'


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import(/* webpackChunkName: "about" */ '../views/404.vue')
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/front/Front'),
    children: [
      {
        path: 'home',
        name: 'FrontHome',
        component: () => import('../views/front/Home.vue')
      },
      {
        path: 'item1',
        name: 'Item1',
        component: () => import('../views/front/Item1.vue')
      },
      {
        path: 'person',
        name: 'FrontPerson',
        component: () => import('../views/front/Person')
      },
    ]
  },
  {
    path: '/frontLogin',
    name: 'FrontLogin',
    component: () => import(/* webpackChunkName: "about" */ '../views/front/Login.vue')
  },
  {
    path: '/frontRegister',
    name: 'FrontRegister',
    component: () => import(/* webpackChunkName: "about" */ '../views/front/Register.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 重置路由
export const resetRouter = () =>{
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

// 刷新页面会导致路由重置
export const setRouter = () =>{
  const storeMenus = localStorage.getItem("menuList")
  if (storeMenus){

    // 获取当前路由对象的名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    // console.log(currentRouteNames)
    if (!currentRouteNames.includes('Manage')){
      //拼装动态路由
      const manageRoute = {path: '/',name:'Manage', component: () => import('../views/Manage.vue'), redirect:'/home', children:[
          {path: '/person', name: '个人信息', component: () => import('../views/Person.vue')}
          ]}
      const menus = JSON.parse(storeMenus)
      menus.forEach(item =>{
        // 当且仅当path(主菜单)不为空时
        if (item.path){
          let itemMenu = {path: item.path.replace("/",""), name: item.name, component: () => import('../views/'+item.pagePath+'.vue')}
          manageRoute.children.push(itemMenu)
        }else if (item.children.length){ // 当子菜单不为空时
          item.children.forEach(childItem => {
            if (childItem.path){
              let itemMenu = {path: childItem.path.replace("/",""), name: childItem.name, component: () => import('../views/'+childItem.pagePath+'.vue')}
              manageRoute.children.push(itemMenu)
            }
          })
        }
      })
      router.addRoute(manageRoute)
    }
  }
}

// 重置就再加载一次
setRouter()

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新

  // 未找到路由
  if (!to.matched.length){
    const storeMenus = localStorage.getItem("menuList")
    if (storeMenus){
      next("/404")  // 放行路由
    }else {
      next("/login")
    }
  }
  // 其他情况都放行
  next()

})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}

export default router
