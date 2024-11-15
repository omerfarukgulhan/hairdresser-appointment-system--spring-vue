<script>
export default {
  name: "HairdresserItem",
  props: {
    hairdresser: {
      type: Object,
      required: true,
    },
  },
  methods: {
    navigateToDetail() {
      this.$router.push({ name: "HairdresserDetail", params: { id: this.hairdresser.id } });
    },
  },
};
</script>

<template>
  <div class="card h-100" @click="navigateToDetail" style="cursor: pointer">
    <img :src="hairdresser.mainImage" class="card-img-top" :alt="hairdresser.bio" />
    <div class="card-body">
      <h5 class="card-title"><strong>{{ hairdresser.shopName }}</strong></h5>
      <p class="card-text">{{ hairdresser.bio }}</p>
      <p><strong>Address:</strong> {{ hairdresser.address }}</p>
      <p><strong>Specialties:</strong> {{ hairdresser.specialties.join(', ') }}</p>
      <p><strong>Average Rating:</strong> {{ hairdresser.averageRating.toFixed(2) }} ({{ hairdresser.numberOfReviews }} reviews)</p>
    </div>
  </div>
</template>

<style scoped>

</style>