<script>
import axios from 'axios';

const serverUrl = import.meta.env.VITE_SERVER_URL + "/treatments";

export default {
  name: "Treatments",
  props: {
    hairdresserId: {
      type: Number,
      required: true,
    }
  },
  data() {
    return {
      treatments: [],
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchTreatments() {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get(`${serverUrl}/hairdresser/${this.hairdresserId}`);
        this.treatments = response.data.data.content;
      } catch (error) {
        this.error = error.message || 'Error fetching treatments';
      } finally {
        this.loading = false;
      }
    },
  },
  created() {
    this.fetchTreatments();
  }
};
</script>

<template>
  <div v-if="treatments.length > 0">
    <h3 class="text-center">Treatments</h3>
    <div class="list-group">
      <div
          v-for="treatment in treatments"
          :key="treatment.id"
          class="list-group-item"
      >
        <div class="d-flex w-100 justify-content-between">
          <h5 class="mb-1">{{ treatment.name }}</h5>
          <small>{{ treatment.duration }} mins</small>
        </div>
        <p class="mb-1">{{ treatment.description }}</p>
        <small>${{ treatment.price }}</small>
      </div>
    </div>
    </div>
  <div v-else>
    <p>No treatments available.</p>
  </div>
</template>

<style scoped>

</style>