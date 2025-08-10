// API utilities (no build, UMD globals)
// Exports: window.Api

(function (global) {
  'use strict';

  var DEFAULTS = Object.freeze({
    resource: 'people',
    sortBy: 'name',
    sortOrder: 'ASC'
  });

  function buildUrl(resource, name, sortBy, sortOrder) {
    var params = new URLSearchParams();
    if (name) params.set('name', name);
    params.set('sort_by', sortBy || DEFAULTS.sortBy);
    params.set('sort_order', sortOrder || DEFAULTS.sortOrder);
    return '/v1/' + resource + '?' + params.toString();
  }

  function safeJson(res) {
    if (!res.ok) {
      return res.text().then(function (txt) {
        throw new Error('Request failed: ' + res.status + ' ' + txt);
      });
    }
    return res.json();
  }

  function fetchContent(url) {
    return fetch(url)
      .then(safeJson)
      .then(function (data) { return (data && Array.isArray(data.content)) ? data.content : []; });
  }

  global.Api = Object.freeze({
    DEFAULTS: DEFAULTS,
    buildUrl: buildUrl,
    fetchContent: fetchContent
  });
})(window);
