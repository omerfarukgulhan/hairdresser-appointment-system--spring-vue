<script>
import axios from 'axios';

const serverUrl = import.meta.env.VITE_SERVER_URL + "/users/update-password";

export default {
  name: "UserPasswordUpdate",
  data() {
    return {
      currentPassword: "",
      confirmPassword: "",
      newPassword: "",
      loading: false,
      error: null,
      userId: this.$store.getters['auth/getUser'].id,
    };
  },
  methods: {
    async updatePassword() {
      this.error = null;
      if (this.confirmPassword !== this.newPassword) {
        this.error = "Passwords do not match.";
        return;
      }

      this.loading = true;
      try {
        await axios.put(
            `${serverUrl}/${this.userId}`,
            {
              oldPassword: this.currentPassword,
              newPassword: this.newPassword,
            },
            {
              headers: {
                Authorization: `${this.$store.getters['auth/getPrefix']} ${this.$store.getters['auth/getToken']}`,
              },
            }
        );
      } catch (err) {
        this.error = err.response?.data?.message || "An unexpected error occurred.";
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<template>
  <div class="card mb-4 mx-auto" style="max-width: 600px;">
    <div class="card-body">
      <h3 class="card-title text-center">Update Password</h3>
      <form @submit.prevent="updatePassword">
        <div v-if="error" class="alert alert-danger">
          {{ error }}
        </div>

        <div class="mb-3">
          <label for="current-password" class="form-label">Current Password</label>
          <input
              type="password"
              class="form-control"
              id="current-password"
              v-model="currentPassword"
              placeholder="Enter current password"
              :disabled="loading"
          />
        </div>
        <div class="mb-3">
          <label for="new-password" class="form-label">New Password</label>
          <input
              type="password"
              class="form-control"
              id="new-password"
              v-model="newPassword"
              placeholder="Enter new password"
              :disabled="loading"
          />
        </div>
        <div class="mb-3">
          <label for="confirm-password" class="form-label">Confirm New Password</label>
          <input
              type="password"
              class="form-control"
              id="confirm-password"
              v-model="confirmPassword"
              placeholder="Confirm new password"
              :disabled="loading"
          />
        </div>
        <div class="text-center">
          <button type="submit" class="btn btn-primary" :disabled="loading">
            <span v-if="loading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
            <span v-else>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
