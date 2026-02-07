import { createRoot } from 'react-dom/client';
// https://github.com/graphql/graphiql/blob/main/packages/graphiql/README.md
import { GraphiQL, HISTORY_PLUGIN } from 'graphiql';
import { explorerPlugin } from '@graphiql/plugin-explorer';
// Fix "Cannot read properties of undefined (reading 'toUrl')"
// https://github.com/suren-atoyan/monaco-react/issues/652#issuecomment-3693013952
import 'graphiql/setup-workers/vite';

import { getAppConfig } from '@app-utils';

import 'graphiql/style.css';
import '@graphiql/plugin-explorer/style.css';

import './main.css';

let app;
const explorer = explorerPlugin();

export function mount(el) {
  const appConfig = getAppConfig();

  const fetcher = createFetcher({
    url: appConfig.api.graphql
  });

  app = createRoot(el);
  app.render(<GraphiQL fetcher={fetcher} plugins={[explorer, HISTORY_PLUGIN]} />);
}

export function umount() {
  app?.umount();
}

function createFetcher({ url }) {
  return async (graphQLParams) => {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(graphQLParams),
    });

    return response.json();
  };
}
