<script>
export default {
  computed: {
    isLoggedIn() {
      return !!this.$store.getters['auth/isAuthenticated'];
    },
    user() {
      return this.$store.getters['auth/getUser'];
    },
  },
  methods: {
    logout() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/');
    },
  },
}
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-dark p-4">
    <div class="container">
      <router-link class="nav-link navbar-brand fs-3 mRTL-2" to="/hairdressers">HairConnect</router-link>

      <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/hairdressers">Hairdressers</router-link>
          </li>
        </ul>

        <ul class="navbar-nav ms-auto">
          <li v-if="isLoggedIn" class="nav-item">
            <router-link class="btn btn-primary me-2" to="/profile">{{ user.firstName }}</router-link>
          </li>
          <li v-else class="nav-item">
            <router-link class="btn btn-primary me-2" to="/login">Login</router-link>
          </li>
          <li v-if="isLoggedIn" class="nav-item">
            <button @click="logout" class="btn btn-warning ms-2">Logout</button>
          </li>
          <li v-else class="nav-item">
            <router-link class="btn btn-primary ms-2" to="/register">Register</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped>
.navbar .btn {
  padding: 0.5rem 1rem;
}

.nav-link {
  color: white;
}
</style>