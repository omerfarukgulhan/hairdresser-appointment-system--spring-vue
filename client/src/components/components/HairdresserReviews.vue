<script>
import axios from 'axios';

const serverUrl = import.meta.env.VITE_SERVER_URL + "/reviews";

export default {
  name: "Reviews",
  props: {
    hairdresserId: {
      type: Number,
      required: true,
    },
    numberOfReviews: {
      type: Number,
      required: true,
    },
    averageRating: {
      type: Number,
      required: true,
    }
  },
  data() {
    return {
      reviews: [],
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchReviews() {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get(`${serverUrl}/${this.hairdresserId}`);
        this.reviews = response.data.data.content;
      } catch (error) {
        this.error = error.message || 'Error fetching reviews';
      } finally {
        this.loading = false;
      }
    },
    formatDate(dateArray) {
      const date = new Date(
          Date.UTC(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5])
      );
      const options = {year: 'numeric', month: 'long', day: 'numeric'};
      return date.toLocaleDateString('en-US', options);
    },
  },
  created() {
    this.fetchReviews();
  },
};
</script>

<template>
  <hr>
  <div class="text-center mb-4">
    <h3 class="mb-1">
      Reviews:
      <span class="text-warning ">
        {{ averageRating.toFixed(2) }}
        <i class="fa fa-star" aria-hidden="true"></i>
      </span>
    </h3>
    <p class="text-muted">Based on {{ numberOfReviews }} review(s)</p>
  </div>

  <div v-if="reviews.length > 0">
    <div v-for="review in reviews" :key="review.id" class="review-item border rounded p-3 mb-3 shadow-sm">
      <div class="d-flex justify-content-between align-items-center">
        <strong class="h5">{{ review.userName }}</strong>
        <span class="text-warning h4">
          {{ review.rating }}
          <i class="fa fa-star" aria-hidden="true"></i>
        </span>
      </div>
      <small class="text-muted ">{{ formatDate(review.createdAt) }}</small>
      <p class="m-0 mt-2">{{ review.comment }}</p>
    </div>
  </div>

  <div v-else class="text-center">
    <p class="text-muted">No reviews yet.</p>
  </div>
</template>


<style scoped>

</style>