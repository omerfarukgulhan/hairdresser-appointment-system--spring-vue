<script>
import axios from 'axios';

const serverUrl = import.meta.env.VITE_SERVER_URL + "/users/activate";

export default {
  name: 'UserActivationPage',
  data() {
    return {
      loading: true,
      success: false,
      error: null,
    };
  },
  async created() {
    const {activationToken} = this.$route.params;
    try {
      const response = await axios.put(`${serverUrl}/${activationToken}`, {token: activationToken});
      if (response.status === 200) {
        this.success = true;
      } else {
        this.error = 'Activation failed. Please try again.';
      }
    } catch (err) {
      this.error = err.response?.data?.message || 'An error occurred during activation.';
    } finally {
      this.loading = false;
    }
  },
};
</script>

<template>
  <div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card shadow-lg p-4 text-center">
      <h1 class="mb-4">Account Activation</h1>
      <div v-if="loading" class="spinner-border text-primary" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
      <div v-if="success" class="alert alert-success" role="alert">
        Your account has been activated successfully!
      </div>
      <div v-if="error" class="alert alert-danger" role="alert">
        {{ error }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 500px;
}

.card {
  border-radius: 10px;
}
</style>
