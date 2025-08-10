// Presentational components (no build, UMD globals)
// Exports: window.Components

(function (global) {
  'use strict';

  var h = global.h; // from utils.js

  function Controls(props) {
    var resource = props.resource,
        onResourceChange = props.onResourceChange,
        name = props.name,
        onNameChange = props.onNameChange,
        sortBy = props.sortBy,
        onSortByChange = props.onSortByChange,
        sortOrder = props.sortOrder,
        onSortOrderChange = props.onSortOrderChange;

    return h('div', { className: 'card mb-4' },
      h('div', { className: 'card-body' },
        h('div', { className: 'row g-3 align-items-end' },
          h('div', { className: 'col-12 col-md-4' },
            h('label', { className: 'form-label' }, 'Choose resource'),
            h('div', { className: 'btn-group w-100', role: 'group' },
              h('input', { type: 'radio', className: 'btn-check', name: 'resource', id: 'people', autoComplete: 'off', checked: resource === 'people', onChange: function () { return onResourceChange('people'); } }),
              h('label', { className: 'btn btn-outline-primary', htmlFor: 'people' }, 'People'),
              h('input', { type: 'radio', className: 'btn-check', name: 'resource', id: 'starships', autoComplete: 'off', checked: resource === 'starships', onChange: function () { return onResourceChange('starships'); } }),
              h('label', { className: 'btn btn-outline-primary', htmlFor: 'starships' }, 'Starships')
            )
          ),
          h('div', { className: 'col-12 col-md-4' },
            h('label', { htmlFor: 'search', className: 'form-label' }, 'Search by name'),
            h('input', { id: 'search', className: 'form-control', placeholder: 'Type a name...', value: name, onChange: function (e) { return onNameChange(e.target.value); } })
          ),
          h('div', { className: 'col-6 col-md-2' },
            h('label', { htmlFor: 'sortBy', className: 'form-label' }, 'Sort by'),
            h('select', { id: 'sortBy', className: 'form-select', value: sortBy, onChange: function (e) { return onSortByChange(e.target.value); } },
              h('option', { value: 'name' }, 'Name'),
              h('option', { value: 'created' }, 'Created')
            )
          ),
          h('div', { className: 'col-6 col-md-2' },
            h('label', { htmlFor: 'sortOrder', className: 'form-label' }, 'Order'),
            h('select', { id: 'sortOrder', className: 'form-select', value: sortOrder, onChange: function (e) { return onSortOrderChange(e.target.value); } },
              h('option', { value: 'ASC' }, 'Ascending'),
              h('option', { value: 'DESC' }, 'Descending')
            )
          )
        )
      )
    );
  }

  function PeopleTable(_a) {
    var items = _a.items;

    return h('div', { className: 'table-responsive' },
      h('table', { className: 'table table-striped table-hover align-middle' },
        h('thead', { className: 'table-light' },
          h('tr', null,
            h('th', null, 'Name'),
            h('th', null, 'Gender'),
            h('th', null, 'Birth Year'),
            h('th', null, 'Created')
          )
        ),
        h('tbody', null,
          items.length === 0
            ? h('tr', null, h('td', { colSpan: '4', className: 'text-center text-muted' }, 'No results'))
            : items.map(function (p, idx) { return h('tr', { key: idx },
                h('td', null, p.name),
                h('td', null, p.gender),
                h('td', null, p.birth_year),
                h('td', null, p.created)
              );
            })
        )
      )
    );
  }

  function StarshipsTable(_a) {
    var items = _a.items;

    return h('div', { className: 'table-responsive' },
      h('table', { className: 'table table-striped table-hover align-middle' },
        h('thead', { className: 'table-light' },
          h('tr', null,
            h('th', null, 'Name'),
            h('th', null, 'Model'),
            h('th', null, 'Manufacturer'),
            h('th', null, 'Created')
          )
        ),
        h('tbody', null,
          items.length === 0
            ? h('tr', null, h('td', { colSpan: '4', className: 'text-center text-muted' }, 'No results'))
            : items.map(function (s, idx) { return h('tr', { key: idx },
                h('td', null, s.name),
                h('td', null, s.model),
                h('td', null, s.manufacturer),
                h('td', null, s.created)
              );
            })
        )
      )
    );
  }

  global.Components = Object.freeze({
    Controls: Controls,
    PeopleTable: PeopleTable,
    StarshipsTable: StarshipsTable
  });
})(window);
