

(function (global) {
  'use strict';

  // Debounce hook using setTimeout; re-computes when value changes after delay
  function useDebounced(value, delay) {
    if (delay === undefined) delay = 400;
    var useState = React.useState;
    var useEffect = React.useEffect;

    var _a = useState(value), debounced = _a[0], setDebounced = _a[1];

    useEffect(function () {
      var t = setTimeout(function () { setDebounced(value); }, delay);
      return function () { clearTimeout(t); };
    }, [value, delay]);

    return debounced;
  }

  // Expose to global
  global.useDebounced = useDebounced;
})(window);
