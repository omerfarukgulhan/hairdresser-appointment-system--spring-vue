<script>
export default {
  data() {
    return {
      email: 'omer@gmail.com',
      password: 'P4ssword',
    };
  },
  computed: {
    loading() {
      return this.$store.getters['auth/isLoading'];
    },
    error() {
      return this.$store.getters['auth/getError'];
    },
  },
  methods: {
    async handleLogin() {
      try {
        await this.$store.dispatch('auth/login', {
          email: this.email,
          password: this.password,
        });
        if (!this.error) {
          this.$router.push('/');
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h3 class="card-title text-center mb-4">Login</h3>
            <form @submit.prevent="handleLogin">
              <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input
                    type="email"
                    id="email"
                    class="form-control"
                    v-model="email"
                    required
                />
              </div>
              <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input
                    type="password"
                    id="password"
                    class="form-control"
                    v-model="password"
                    required
                />
              </div>
              <button type="submit" class="btn btn-primary w-100" :disabled="loading">
                <span v-if="loading" class="spinner-border spinner-border-sm me-2" role="status"
                      aria-hidden="true"></span>
                Login
              </button>
              <div v-if="error" class="alert alert-danger mt-3" role="alert">
                {{ error }}
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  max-width: 400px;
  margin: auto;
}
</style>
