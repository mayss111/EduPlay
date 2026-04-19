import { environment } from '../../../environments/environment';

export const API_BASE_URL = environment.apiBaseUrl;

export const API_ENDPOINTS = {
  auth: `${API_BASE_URL}/auth`,
  game: `${API_BASE_URL}/game`,
  leaderboard: `${API_BASE_URL}/leaderboard`
};
