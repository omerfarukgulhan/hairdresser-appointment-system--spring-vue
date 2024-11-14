import {createRouter, createWebHistory} from "vue-router";
import store from "./store/index.js"
import HairdressersMainPage from "@/pages/hairdressers/HairdressersMainPage.vue";
import LoginPage from "@/pages/auth/LoginPage.vue";
import RegisterPage from "@/pages/auth/RegisterPage.vue";
import NotFound from "@/pages/NotFound.vue";


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {path: '/', component: HairdressersMainPage},
    {path: '/hairdressers', component: HairdressersMainPage},
    {path: '/login', component: LoginPage, meta: {requiresAuth: false}},
    {path: '/register', component: RegisterPage, meta: {requiresAuth: false}},
    {path: '/:notFound(.*)', component: NotFound},
  ],
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters['auth/isAuthenticated'];
  const isAdmin = store.getters['auth/isAdmin'];
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login');
  } else if (to.meta.requiresAdmin && !isAdmin) {
    next('/');
  } else {
    next();
  }
});

export default router;