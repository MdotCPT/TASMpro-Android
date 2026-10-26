window.TASMPhotoStore = (() => {
  const DB_NAME = "tasmpro_photos";
  const STORE = "photos";
  function open() {
    return new Promise((resolve, reject) => {
      if (!window.indexedDB) return reject(new Error("IndexedDB unavailable"));
      const request = indexedDB.open(DB_NAME, 1);
      request.onupgradeneeded = () => {
        const db = request.result;
        if (!db.objectStoreNames.contains(STORE)) db.createObjectStore(STORE);
      };
      request.onsuccess = () => resolve(request.result);
      request.onerror = () => reject(request.error);
    });
  }
  async function access(mode, action) {
    const db = await open();
    return new Promise((resolve, reject) => {
      const tx = db.transaction(STORE, mode);
      const request = action(tx.objectStore(STORE));
      request.onsuccess = () => resolve(request.result);
      request.onerror = () => reject(request.error);
      tx.oncomplete = () => db.close();
      tx.onabort = () => { db.close(); reject(tx.error); };
    });
  }
  return {
    put: (key, value) => access("readwrite", store => store.put(value, key)),
    get: key => access("readonly", store => store.get(key)),
    remove: key => access("readwrite", store => store.delete(key)),
    ready: async () => { const db = await open(); db.close(); return true; }
  };
})();
