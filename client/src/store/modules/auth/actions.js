import axios from 'axios';

const serverUrl = import.meta.env.VITE_SERVER_URL + "/auth";
const profileImageUrl = import.meta.env.VITE_SERVER_ASSETS_URL + "/profile/default.png";

export default {
  async login(context, payload) {
    context.commit('SET_LOADING', true);
    context.commit('CLEAR_ERROR');
    try {
      const response = await axios.post(`${serverUrl}/login`, payload);
      if (response.data.success) {
        const user = response.data.data.userResponse;
        const {token, prefix} = response.data.data.token;
        user.profileImage = `${profileImageUrl}/${user.profileImage}`;
        context.commit('SET_TOKEN', token);
        context.commit('SET_PREFIX', prefix);
        context.commit('SET_USER', user);
      } else {
        throw new Error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.data) {
        const errorMessage = error.response.data.message || 'An error occurred';
        context.commit('SET_ERROR', errorMessage);
      } else {
        context.commit('SET_ERROR', error.message);
      }
    } finally {
      context.commit('SET_LOADING', false);
    }
  },
  async register(context, payload) {
    context.commit('SET_LOADING', true);
    context.commit('CLEAR_ERROR');
    try {
      const response = await axios.post(`${serverUrl}/register`, payload);
      if (response.data.success) {
        context.commit('SET_PREFIX', "Bearer");
      } else {
        throw new Error(response.data.message);
      }
    } catch (error) {
      context.commit('SET_ERROR', error.message);
    } finally {
      context.commit('SET_LOADING', false);
    }
  },
  logout(context) {
    context.commit('CLEAR_AUTH');
  },
};
