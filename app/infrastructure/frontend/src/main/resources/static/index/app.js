// App entry (JSX, transpiled by Babel). Depends on utils.js, api.js, components.js
(function (global) {
  'use strict';

  const { useEffect, useMemo, useState } = React;

  const useDebounced = global.useDebounced;
  const Api = global.Api;
  const { Controls, PeopleTable, StarshipsTable } = global.Components;

  function App() {
    const [resource, setResource] = useState(Api.DEFAULTS.resource);
    const [name, setName] = useState('');
    const [sortBy, setSortBy] = useState(Api.DEFAULTS.sortBy);
    const [sortOrder, setSortOrder] = useState(Api.DEFAULTS.sortOrder);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [items, setItems] = useState([]);

    const debouncedName = useDebounced(name, 400);

    const url = useMemo(() => Api.buildUrl(resource, debouncedName, sortBy, sortOrder), [resource, debouncedName, sortBy, sortOrder]);

    useEffect(() => {
      let active = true;
      setLoading(true);
      setError(null);

      Api.fetchContent(url)
        .then((content) => {
          if (!active) return;
          setItems(content);
        })
        .catch((err) => {
          if (!active) return;
          setError((err && err.message) || 'Unknown error');
          setItems([]);
        })
        .finally(() => {
          if (active) setLoading(false);
        });

      return () => { active = false; };
    }, [url]);

    return (
      <>
        <Controls
          resource={resource}
          onResourceChange={setResource}
          name={name}
          onNameChange={setName}
          sortBy={sortBy}
          onSortByChange={setSortBy}
          sortOrder={sortOrder}
          onSortOrderChange={setSortOrder}
        />

        {error && (
          <div className="alert alert-danger" role="alert">{error}</div>
        )}

        {loading ? (
          <div className="d-flex align-items-center gap-2">
            <div className="spinner-border" role="status" aria-hidden="true"></div>
            <span>Loading...</span>
          </div>
        ) : resource === 'people' ? (
          <PeopleTable items={items} />
        ) : (
          <StarshipsTable items={items} />
        )}
      </>
    );
  }

  const root = ReactDOM.createRoot(document.getElementById('root'));
  root.render(<App />);
})(window);
