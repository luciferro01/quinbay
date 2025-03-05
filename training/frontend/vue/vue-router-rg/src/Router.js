import {
  createMemoryHistory,
  createWebHistory,
  createRouter,
} from "vue-router";

import HomeView from "./components/HomeView.vue";
import AboutView from "./components/AboutView.vue";
import UserView from "./components/UserView.vue";
import UserProfile from "./components/UserProfile.vue";
import UserPosts from "./components/UserPosts.vue";

const routes = [
  { path: "/", component: HomeView },
  // { path: "/:id", component: HomeView },
  { path: "/about", component: AboutView },
  // { path: "/dynamic/:id", component: AboutView },
  // { path: "/withQuery", component: AboutView },
  // { path: "/withPath/", component: AboutView },
  {
    path: "/user/:id",
    component: UserView,
    children: [
      {
        path: "profile",
        name: "profile",
        component: UserProfile,
      },
      {
        path: "posts",
        name: "posts",
        component: UserPosts,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  // history: createMemoryHistory(),
  routes,
});

export default router;
