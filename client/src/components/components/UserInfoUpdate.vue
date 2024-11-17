<script>
import axios from 'axios';

const serverUrl = import.meta.env.VITE_SERVER_URL + "/users";

export default {
  name: "UserInfo",
  data() {
    return {
      user: this.$store.getters['auth/getUser'],
      firstName: this.$store.getters['auth/getUser'].firstName || '',
      lastName: this.$store.getters['auth/getUser'].lastName || '',
      phoneNumber: this.$store.getters['auth/getUser'].phoneNumber || '',
      profileImage: this.$store.getters['auth/getUser'].profileImage || null,
      previewImage: this.$store.getters['auth/getUser'].profileImage || null,
      isLoading: false,
      error: null,
    };
  },
  methods: {
    async updateUser() {
      this.isLoading = true;
      this.error = null;
      try {
        const formData = new FormData();
        formData.append('firstName', this.firstName);
        formData.append('lastName', this.lastName);
        formData.append('phoneNumber', this.phoneNumber);

        if (this.profileImage instanceof File) {
          formData.append('profileImage', this.profileImage);
        }

        const response = await axios.put(`${serverUrl}/${this.user.id}`, formData, {
          headers: {
            Authorization: `${this.$store.getters['auth/getPrefix']} ${this.$store.getters['auth/getToken']}`,
            'Content-Type': 'multipart/form-data',
          },
        });

        this.$store.dispatch('auth/updateUser', {
          firstName: this.firstName,
          lastName: this.lastName,
          phoneNumber: this.phoneNumber,
          profileImage: response.data.data.profileImage
        });
      } catch (error) {
        console.error('Error updating user info:', error);
        this.error = error.response?.data?.message || "Failed to update user information.";
      } finally {
        this.isLoading = false;
      }
    },
    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.profileImage = file;
        const reader = new FileReader();
        reader.onload = (e) => {
          this.previewImage = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
  },
};
</script>

<template>
  <div class="card mb-4 mx-auto" style="max-width: 600px;">
    <div class="card-body">
      <h3 class="card-title text-center">User Information</h3>
      <div v-if="error" class="alert alert-danger">
        {{ error }}
      </div>

      <div class="row">
        <div class="col-md-4 text-center">
          <img
              :src="previewImage"
              alt="User Profile Image"
              class="img-fluid rounded-circle mb-3"
          />
          <input
              type="file"
              class="form-control"
              id="image-upload"
              @change="handleFileUpload"
          />
        </div>
        <div class="col-md-8">
          <form @submit.prevent="updateUser">
            <div class="mb-3">
              <label for="first-name" class="form-label">First Name</label>
              <input
                  type="text"
                  class="form-control"
                  v-model="firstName"
                  :disabled="isLoading"
              />
            </div>
            <div class="mb-3">
              <label for="last-name" class="form-label">Last Name</label>
              <input
                  type="text"
                  class="form-control"
                  v-model="lastName"
                  :disabled="isLoading"
              />
            </div>
            <div class="mb-3">
              <label for="phone-number" class="form-label">Phone number</label>
              <input
                  class="form-control"
                  v-model="phoneNumber"
                  :disabled="isLoading"
              />
            </div>
            <div class="text-center">
              <button
                  type="submit"
                  class="btn btn-primary"
                  :disabled="isLoading"
              >
                <span v-if="isLoading" class="spinner-border spinner-border-sm me-2"></span>
                Save
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
