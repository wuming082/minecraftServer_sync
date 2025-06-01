import Vue from "vue";
import Router from "vue-router";
import Login from "../components/LoginContainer.vue"
import Dash from "../DashBoard.vue";

Vue.use(Router);

const routes = [
    {
        path: '/',
        name: 'login',
        component: Login,
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dash,
        meta: { requiresAuth: true }
    }
];

const router = new Router({
    routes,
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token'); // 获取存储的令牌
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

    if (requiresAuth && !token) {
        next('/'); // 如果路由需要认证但没有令牌，则重定向到登录页
    } else if (!requiresAuth && token && to.path === '/') {
        next('/dashboard'); // 如果已认证且尝试访问登录页，则重定向到仪表盘
    } else {
        next(); // 允许导航
    }
});



export default router;