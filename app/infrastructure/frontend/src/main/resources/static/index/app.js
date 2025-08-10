// App entry (no JSX, no build). Depends on utils.js, api.js, components.js
(function (global) {
  'use strict';

  var useEffect = React.useEffect;
  var useMemo = React.useMemo;
  var useState = React.useState;

  var h = global.h;
  var useDebounced = global.useDebounced;
  var Api = global.Api;
  var Controls = global.Components.Controls;
  var PeopleTable = global.Components.PeopleTable;
  var StarshipsTable = global.Components.StarshipsTable;

  function App() {
    var _useState = useState(Api.DEFAULTS.resource), resource = _useState[0], setResource = _useState[1];
    var _useState2 = useState(''), name = _useState2[0], setName = _useState2[1];
    var _useState3 = useState(Api.DEFAULTS.sortBy), sortBy = _useState3[0], setSortBy = _useState3[1];
    var _useState4 = useState(Api.DEFAULTS.sortOrder), sortOrder = _useState4[0], setSortOrder = _useState4[1];
    var _useState5 = useState(false), loading = _useState5[0], setLoading = _useState5[1];
    var _useState6 = useState(null), error = _useState6[0], setError = _useState6[1];
    var _useState7 = useState([]), items = _useState7[0], setItems = _useState7[1];

    var debouncedName = useDebounced(name, 400);

    var url = useMemo(function () {
      return Api.buildUrl(resource, debouncedName, sortBy, sortOrder);
    }, [resource, debouncedName, sortBy, sortOrder]);

    useEffect(function () {
      var active = true;
      setLoading(true);
      setError(null);

      Api.fetchContent(url)
        .then(function (content) {
          if (!active) return;
          setItems(content);
        })
        .catch(function (err) {
          if (!active) return;
          setError((err && err.message) || 'Unknown error');
          setItems([]);
        })
        .finally(function () {
          if (active) setLoading(false);
        });

      return function () { active = false; };
    }, [url]);

    return h(React.Fragment, null,
      h(Controls, {
        resource: resource,
        onResourceChange: setResource,
        name: name,
        onNameChange: setName,
        sortBy: sortBy,
        onSortByChange: setSortBy,
        sortOrder: sortOrder,
        onSortOrderChange: setSortOrder
      }),
      error && h('div', { className: 'alert alert-danger', role: 'alert' }, error),
      loading
        ? h('div', { className: 'd-flex align-items-center gap-2' },
            h('div', { className: 'spinner-border', role: 'status', 'aria-hidden': 'true' }),
            h('span', null, 'Loading...')
          )
        : (resource === 'people'
            ? h(PeopleTable, { items: items })
            : h(StarshipsTable, { items: items })
          )
    );
  }

  var root = ReactDOM.createRoot(document.getElementById('root'));
  root.render(h(App));
})(window);
