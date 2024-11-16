<script>
import UserInfoUpdate from "@/components/components/UserInfoUpdate.vue";
import UserPasswordUpdate from "@/components/components/UserPasswordUpdate.vue";

export default {
  name: "UserProfile",
  components: {
    UserInfoUpdate,
    UserPasswordUpdate,
  }
};
</script>

<template>
  <div class="container my-4">
    <h1 class="text-center mb-4">User Profile</h1>
    <UserInfoUpdate/>
    <UserPasswordUpdate/>
    <div class="d-flex justify-content-between">
      <button class="btn btn-primary" type="button">Apply as Hairdresser</button>
      <button class="btn btn-danger" type="button">Delete Account</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 800px;
}
</style>