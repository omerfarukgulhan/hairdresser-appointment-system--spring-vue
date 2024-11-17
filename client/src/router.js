import {createRouter, createWebHistory} from "vue-router";
import store from "./store/index.js"
import HairdressersMainPage from "@/pages/hairdressers/HairdressersMainPage.vue";
import LoginPage from "@/pages/auth/LoginPage.vue";
import RegisterPage from "@/pages/auth/RegisterPage.vue";
import NotFound from "@/pages/NotFound.vue";
import HairdresserDetailPage from "@/pages/hairdressers/HairdresserDetailPage.vue";
import UserProfilePage from "@/pages/user/UserProfilePage.vue";
import UserActivationPage from "@/pages/auth/UserActivationPage.vue";
import HairdresserDashboardPage from "@/pages/hairdresser-management/HairdresserDashboardPage.vue";
import HairdresserTreatmentsPage from "@/pages/hairdresser-management/HairdresserTreatmentsPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {path: "/", redirect: "/hairdressers"},
    {path: "/hairdressers", name: "Hairdressers", component: HairdressersMainPage},
    {path: "/hairdressers/:id", name: "HairdresserDetail", component: HairdresserDetailPage, props: true},
    {path: "/profile", name: "UserProfile", component: UserProfilePage, meta: {requiresAuth: true}},
    {path: "/login", name: "Login", component: LoginPage, meta: {requiresAuth: false}},
    {path: "/register", name: "Register", component: RegisterPage, meta: {requiresAuth: false}},
    {
      path: "/users/activate/:activationToken",
      name: "UserActivation",
      component: UserActivationPage,
      meta: {requiresAuth: false}
    },
    {
      path: "/hairdresser/dashboard",
      name: "HairdresserDashboard",
      component: HairdresserDashboardPage,
      meta: {requiresAuth: true, requiresHairdresser: true},
    },
    {
      path: "/hairdresser/treatments",
      name: "ManageTreatments",
      component: HairdresserTreatmentsPage,
      meta: {requiresAuth: true, requiresHairdresser: true},
    },
    {path: "/:notFound(.*)", name: "NotFound", component: NotFound},
  ],
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters["auth/isAuthenticated"];
  const isAdmin = store.getters["auth/isAdmin"];
  const isHairdresser = store.getters["auth/isHairdresser"];

  if (to.meta.requiresAuth && !isAuthenticated) {
    next("/login");
  } else if (to.meta.requiresAdmin && !isAdmin) {
    next("/");
  } else if (to.meta.requiresHairdresser && !isHairdresser) {
    next("/login");
  } else {
    next();
  }
});

export default router;