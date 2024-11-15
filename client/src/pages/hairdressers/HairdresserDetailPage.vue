<script>
import axios from 'axios';
import HairdresserTreatments from "@/components/components/HairdresserTreatments.vue";
import HairdresserReviews from "@/components/components/HairdresserReviews.vue";

const serverUrl = import.meta.env.VITE_SERVER_URL + "/hairdressers";
const hairdresserMainImageUrl = import.meta.env.VITE_SERVER_ASSETS_URL + "/hairdresser/main-image/";
const hairdresserSideImagesUrl = import.meta.env.VITE_SERVER_ASSETS_URL + "/hairdresser/side-images/";

export default {
  name: "HairdresserDetail",
  components: {
    HairdresserReviews,
    HairdresserTreatments,
  },
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      hairdresser: null,
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchHairdresser(id) {
      this.loading = true;
      this.error = null;
      try {
        const response = await axios.get(`${serverUrl}/${id}`);
        this.hairdresser = response.data.data;
        this.hairdresser.mainImage = hairdresserMainImageUrl + this.hairdresser.mainImage;
        this.hairdresser.sideImages = this.hairdresser.sideImages.map(sideImage =>
            hairdresserSideImagesUrl + sideImage
        );
      } catch (error) {
        this.error = error.message || 'Error fetching hairdresser details';
      } finally {
        this.loading = false;
      }
    },
  },
  created() {
    this.fetchHairdresser(this.id);
  },
};
</script>

<template>
  <div class="container mt-4">
    <div v-if="loading" class="text-center">
      <p>Loading...</p>
    </div>

    <div v-if="error" class="alert alert-danger text-center">
      <p>{{ error }}</p>
    </div>

    <div v-else-if="hairdresser" class="container py-4">
      <h1 class="text-center mb-4">{{ hairdresser.shopName }}</h1>
      <div class="row g-4">
        <div class="col-md-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body d-flex align-items-center justify-content-center">
              <div
                  id="hairdresserCarousel"
                  class="carousel slide"
                  data-bs-ride="carousel"
                  style="width: 100%;"
              >
                <div class="carousel-inner">
                  <div class="carousel-item active">
                    <div class="image-wrapper">
                      <img :src="hairdresser.mainImage" alt="Main Image"/>
                    </div>
                  </div>
                  <div
                      v-for="(image, index) in hairdresser.sideImages"
                      :key="'side-image-' + index"
                      class="carousel-item"
                  >
                    <div class="image-wrapper">
                      <img :src="image" :alt="'Side Image ' + (index + 1)"/>
                    </div>
                  </div>
                </div>
                <button
                    class="carousel-control-prev"
                    type="button"
                    data-bs-target="#hairdresserCarousel"
                    data-bs-slide="prev"
                >
                  <span class="carousel-control-prev-icon custom-prev-arrow" aria-hidden="true"></span>
                  <span class="visually-hidden">Previous</span>
                </button>
                <button
                    class="carousel-control-next"
                    type="button"
                    data-bs-target="#hairdresserCarousel"
                    data-bs-slide="next"
                >
                  <span class="carousel-control-next-icon custom-next-arrow" aria-hidden="true"></span>
                  <span class="visually-hidden">Next</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body">
              <h5 class="card-title">About</h5>
              <p class="card-text fs-5"><strong>Bio:</strong> {{ hairdresser.bio }}</p>
              <p class="card-text fs-5"><strong>Address:</strong> {{ hairdresser.address }}</p>
              <p class="card-text fs-5"><strong>Specialties:</strong> {{ hairdresser.specialties.join(', ') }}</p>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="h-100">
            <HairdresserTreatments :hairdresserId="hairdresser.id"/>
          </div>
        </div>
      </div>
      <div class="mt-4">
        <HairdresserReviews
            :hairdresserId="hairdresser.id"
            :numberOfReviews="hairdresser.numberOfReviews"
            :averageRating="hairdresser.averageRating"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.image-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 350px;
  width: 100%;
  background-color: white;
  overflow: hidden;
  position: relative;
}

.image-wrapper img {
  max-height: 100%;
  max-width: 100%;
  object-fit: contain;
}

.card {
  height: 100%;
}

.custom-prev-arrow,
.custom-next-arrow {
  background-size: 100%;
  background-repeat: no-repeat;
  width: 2rem;
  height: 2rem;
}

.custom-prev-arrow {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000000' viewBox='0 0 16 16'%3E%3Cpath d='M11.354 1.354a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z'/%3E%3C/svg%3E");
}

.custom-next-arrow {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000000' viewBox='0 0 16 16'%3E%3Cpath d='M4.646 1.354a.5.5 0 0 0 0 .708L10.293 8l-5.647 5.646a.5.5 0 0 0 .708.708l6-6a.5.5 0 0 0 0-.708l-6-6a.5.5 0 0 0-.708 0z'/%3E%3C/svg%3E");
}
</style>
