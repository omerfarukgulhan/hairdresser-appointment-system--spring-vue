<script>
export default {
  name: "UserPasswordUpdate",
  data() {
    return {
      currentPassword: "",
      confirmPassword: "",
      newPassword: "",
    }
  }
}
</script>

<template>
  <div class="card mb-4 mx-auto" style="max-width: 600px;">
    <div class="card-body">
      <h3 class="card-title text-center">Update Password</h3>
      <form @submit.prevent="savePassword">
        <div class="mb-3">
          <label for="current-password" class="form-label">Current Password</label>
          <input
              type="password"
              class="form-control"
              id="current-password"
              v-model="currentPassword"
              placeholder="Enter current password"
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
          />
        </div>
        <div class="text-center">
          <button type="button" class="btn btn-primary">Save</button>
        </div>
      </form>
    </div>
  </div>
</template>


