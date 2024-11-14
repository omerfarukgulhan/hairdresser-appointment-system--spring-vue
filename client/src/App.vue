<script>
import {defineComponent} from "vue";
import TheHeader from "@/components/layout/TheHeader.vue";

export default defineComponent({
  components: {TheHeader}
})
</script>

<template>
  <the-header></the-header>
  <router-view></router-view>
</template>

<style scoped></style>
