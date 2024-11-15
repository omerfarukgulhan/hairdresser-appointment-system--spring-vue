<script>
import axios from 'axios';
import HairdresserItem from "@/components/components/HairdresserItem.vue";

const serverUrl = import.meta.env.VITE_SERVER_URL + "/hairdressers";
const hairdresserMainImageUrl = import.meta.env.VITE_SERVER_ASSETS_URL + "/hairdresser/main-image/";

export default {
  name: "Hairdressers",
  components: {
    HairdresserItem,
  },
  data() {
    return {
      hairdressers: [],
      currentPage: 1,
      pageSize: 9,
      hasMore: false,
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchHairdressers(page) {
      if (page < 1) return;
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get(serverUrl, {
          params: {page: page - 1, size: this.pageSize},
        });
        const data = response.data.data;
        this.hairdressers = data.content.map(hairdresser => ({
          ...hairdresser,
          mainImage: hairdresserMainImageUrl + hairdresser.mainImage,
        }));
        this.currentPage = page;
        this.hasMore = data.totalPages > page;
      } catch (error) {
        this.error = error.message || 'Error fetching hairdressers';
      } finally {
        this.loading = false;
      }
    },
  },
  created() {
    this.fetchHairdressers(this.currentPage);
  },
};
</script>

<template>
  <div class="container mt-4">
    <div class="row">
      <div v-for="hairdresser in hairdressers" :key="hairdresser.id" class="col-md-4 mb-4">
        <HairdresserItem :hairdresser="hairdresser"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>