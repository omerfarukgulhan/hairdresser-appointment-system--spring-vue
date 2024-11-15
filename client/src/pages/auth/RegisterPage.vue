<script>
export default {
  data() {
    return {
      email: 'faruk@faruk.com',
      username: 'username2',
      password: 'P4ssword',
    };
  },
  methods: {
    async handleRegister() {
      this.loading = true;
      this.error = '';
      try {
        await this.$store.dispatch('auth/register', {
          email: this.email,
          username: this.username,
          password: this.password,
        });
        alert('Please activate your email');
        this.$router.push('/login');
      } catch (err) {
        this.error = err.response?.data?.message || 'Registration failed. Please try again.';
      } finally {
        this.loading = false;
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
            <h3 class="card-title text-center mb-4">Register</h3>
            <form @submit.prevent="handleRegister">
              <div class="mb-3">
                <label for="name" class="form-label">First Name</label>
                <input
                    type="text"
                    id="name"
                    class="form-control"
                    v-model="name"
                    required
                />
              </div>
              <div class="mb-3">
                <label for="email" class="form-label">Last Name</label>
                <input
                    type="email"
                    id="email"
                    class="form-control"
                    v-model="email"
                    required
                />
              </div>
              <div class="mb-3">
                <label for="password" class="form-label">Email</label>
                <input
                    type="password"
                    id="password"
                    class="form-control"
                    v-model="password"
                    required
                />
              </div>
              <div class="mb-3">
                <label for="confirmPassword" class="form-label">Password</label>
                <input
                    type="password"
                    id="confirmPassword"
                    class="form-control"
                    v-model="confirmPassword"
                    required
                />
              </div>
              <button type="submit" class="btn btn-primary w-100">
                Register
              </button>
              <div v-if="error" class="alert alert-danger mt-3" role="alert">
                {{ error }}
              </div>
            </form>
            <p class="text-center mt-3">
              Already have an account?
              <router-link class="me-2" to="/login">Login here</router-link>
            </p>
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