(function () {
  'use strict';

  const store = window.TASMPhotoStore;

  window.TASMPhotoIntegration = {
    available: async function () {
      if (!store) return false;
      try {
        return await store.ready();
      } catch (error) {
        console.error('Photo storage unavailable:', error);
        return false;
      }
    },

    savePhoto: async function (key, photo) {
      if (!await this.available()) {
        throw new Error('Photo storage unavailable');
      }
      await store.put(key, photo);
      return key;
    },

    loadPhoto: async function (key) {
      if (!await this.available()) {
        throw new Error('Photo storage unavailable');
      }
      return await store.get(key);
    },

    deletePhoto: async function (key) {
      if (!await this.available()) {
        throw new Error('Photo storage unavailable');
      }
      await store.remove(key);
    }
  };
})();
