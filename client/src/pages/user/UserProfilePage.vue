<script>
import UserInfoUpdate from "@/components/components/UserInfoUpdate.vue";
import UserPasswordUpdate from "@/components/components/UserPasswordUpdate.vue";
import axios from "axios";

const serverUrlDelete = import.meta.env.VITE_SERVER_URL + "/users";
const serverUrlApplyHairdresser = import.meta.env.VITE_SERVER_URL + "/hairdressers";

export default {
  name: "UserProfile",
  components: {
    UserInfoUpdate,
    UserPasswordUpdate,
  },
  data() {
    return {
      isLoading: false,
      error: null,
    };
  },
  methods: {
    async handleApplyForHairdresser() {
      this.error = null;
      this.isLoading = true;
      try {
        const response = await axios.post(
            serverUrlApplyHairdresser,
            {},
            {
              headers: {
                Authorization: `${this.$store.getters['auth/getPrefix']} ${this.$store.getters['auth/getToken']}`,
              },
            }
        );
        console.log("Applied successfully:", response.data);
        alert("Your application has been submitted!");
      } catch (err) {
        console.error("Error applying as hairdresser:", err);
        this.error = err.response?.data?.message || "Failed to apply as a hairdresser.";
      } finally {
        this.isLoading = false;
      }
    },
    async handleDeleteUser() {
      this.error = null;
      this.isLoading = true;
      try {
        const response = await axios.delete(serverUrlDelete, {
          headers: {
            Authorization: `${this.$store.getters['auth/getPrefix']} ${this.$store.getters['auth/getToken']}`,
          },
        });
        console.log("User deleted successfully:", response.data);
        alert("Your account has been deleted. Redirecting to the home page...");
        this.$router.push("/");
      } catch (err) {
        console.error("Error deleting account:", err);
        this.error = err.response?.data?.message || "Failed to delete account.";
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<template>
  <div class="container my-4">
    <h1 class="text-center mb-4">User Profile</h1>
    <UserInfoUpdate/>
    <UserPasswordUpdate/>
    <div v-if="error" class="alert alert-danger mt-3">
      {{ error }}
    </div>
    <div class="d-flex justify-content-between mt-4">
      <button
          class="btn btn-primary"
          type="button"
          :disabled="isLoading"
          @click="handleApplyForHairdresser"
      >
        <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
        <span v-else>Apply as Hairdresser</span>
      </button>
      <button
          class="btn btn-danger"
          type="button"
          :disabled="isLoading"
          @click="handleDeleteUser"
      >
        <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
        <span v-else>Delete Account</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 800px;
}
</style>