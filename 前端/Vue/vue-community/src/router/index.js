import Vue from 'vue'


const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '*',
    name: '404',
    component: () => import('../views/Error.vue')
  },
]

const router = new VueRouter({
  base: process.env.BASE_URL,
  routes
})


// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus) {
    // 拼装动态路由
    const manageRoute = { path: '/', name: 'Manage', component: () => import('../views/Manage.vue'), redirect: "/home", children: [] }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if (item.path) {  // 当且仅当path不为空的时候才去设置路由
        let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
        manageRoute.children.push(itemMenu)
      } else if(item.children.length) {
        item.children.forEach(item => {
          if (item.path) {
            let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue')}
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })
    let person = { path: 'person', name: '个人信息', component: () => import('../views/Person.vue')}
    manageRoute.children.push(person)

    let editPwd = { path: 'editPwd', name: '修改密码', component: () => import('../views/EditPwd.vue')}
    // 获取当前的路由对象名称数组
    const currentRouteNames = router.getRoutes().map(v => v.name)
    if (!currentRouteNames.includes('Manage')) {
      // 动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }

  }
}

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  // 放行路由
  if (to.path === '/login' || to.path === '/register') {
    next();
  } else {
    let token = localStorage.getItem('token');
    if (token === null || token === '') {
      next('/login');
    } else {
      next();
    }
  }
})


export default router
