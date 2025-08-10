const { useEffect, useMemo, useState } = React;

function Controls({ resource, onResourceChange, name, onNameChange, sortBy, onSortByChange, sortOrder, onSortOrderChange }) {
  return (
    <div className="card mb-4">
      <div className="card-body">
        <div className="row g-3 align-items-end">
          <div className="col-12 col-md-4">
            <label className="form-label">Choose resource</label>
            <div className="btn-group w-100" role="group">
              <input type="radio" className="btn-check" name="resource" id="people" autoComplete="off" checked={resource === 'people'} onChange={() => onResourceChange('people')} />
              <label className="btn btn-outline-primary" htmlFor="people">People</label>
              <input type="radio" className="btn-check" name="resource" id="starships" autoComplete="off" checked={resource === 'starships'} onChange={() => onResourceChange('starships')} />
              <label className="btn btn-outline-primary" htmlFor="starships">Starships</label>
            </div>
          </div>

          <div className="col-12 col-md-4">
            <label htmlFor="search" className="form-label">Search by name</label>
            <input id="search" className="form-control" placeholder="Type a name..." value={name} onChange={(e) => onNameChange(e.target.value)} />
          </div>

          <div className="col-6 col-md-2">
            <label htmlFor="sortBy" className="form-label">Sort by</label>
            <select id="sortBy" className="form-select" value={sortBy} onChange={(e) => onSortByChange(e.target.value)}>
              <option value="name">Name</option>
              <option value="created">Created</option>
            </select>
          </div>

          <div className="col-6 col-md-2">
            <label htmlFor="sortOrder" className="form-label">Order</label>
            <select id="sortOrder" className="form-select" value={sortOrder} onChange={(e) => onSortOrderChange(e.target.value)}>
              <option value="ASC">Ascending</option>
              <option value="DESC">Descending</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  );
}

function PeopleTable({ items }) {
  return (
    <div className="table-responsive">
      <table className="table table-striped table-hover align-middle">
        <thead className="table-light">
          <tr>
            <th>Name</th>
            <th>Gender</th>
            <th>Birth Year</th>
            <th>Created</th>
          </tr>
        </thead>
        <tbody>
          {items.length === 0 ? (
            <tr><td colSpan="4" className="text-center text-muted">No results</td></tr>
          ) : items.map((p, idx) => (
            <tr key={idx}>
              <td>{p.name}</td>
              <td>{p.gender}</td>
              <td>{p.birth_year}</td>
              <td>{p.created}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function StarshipsTable({ items }) {
  return (
    <div className="table-responsive">
      <table className="table table-striped table-hover align-middle">
        <thead className="table-light">
          <tr>
            <th>Name</th>
            <th>Model</th>
            <th>Manufacturer</th>
            <th>Created</th>
          </tr>
        </thead>
        <tbody>
          {items.length === 0 ? (
            <tr><td colSpan="4" className="text-center text-muted">No results</td></tr>
          ) : items.map((s, idx) => (
            <tr key={idx}>
              <td>{s.name}</td>
              <td>{s.model}</td>
              <td>{s.manufacturer}</td>
              <td>{s.created}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function useDebounced(value, delay = 400) {
  const [debounced, setDebounced] = useState(value);
  useEffect(() => {
    const t = setTimeout(() => setDebounced(value), delay);
    return () => clearTimeout(t);
  }, [value, delay]);
  return debounced;
}

function App() {
  const [resource, setResource] = useState('people');
  const [name, setName] = useState('');
  const [sortBy, setSortBy] = useState('name');
  const [sortOrder, setSortOrder] = useState('ASC');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [items, setItems] = useState([]);

  const debouncedName = useDebounced(name, 400);

  const url = useMemo(() => {
    const params = new URLSearchParams();
    if (debouncedName) params.set('name', debouncedName);
    params.set('sort_by', sortBy);
    params.set('sort_order', sortOrder);
    return `/v1/${resource}?${params.toString()}`;
  }, [resource, debouncedName, sortBy, sortOrder]);

  useEffect(() => {
    let active = true;
    setLoading(true);
    setError(null);

    fetch(url)
      .then(async (res) => {
        if (!res.ok) {
          const txt = await res.text();
          throw new Error(`Request failed: ${res.status} ${txt}`);
        }
        return res.json();
      })
      .then((data) => {
        if (!active) return;
        const content = Array.isArray(data?.content) ? data.content : [];
        setItems(content);
      })
      .catch((err) => {
        if (!active) return;
        setError(err.message || 'Unknown error');
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
        <div className="d-flex align-items-center gap-2"><div className="spinner-border" role="status" aria-hidden="true"></div><span>Loading...</span></div>
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
