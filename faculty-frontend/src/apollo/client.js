import { ApolloClient, InMemoryCache, HttpLink, from } from "@apollo/client";
import { onError } from "@apollo/client/link/error";
import { setContext } from "@apollo/client/link/context";
import { toast } from "sonner";

import { getToken, logout } from "../utils/auth";

const httpLink = new HttpLink({
  uri: "http://localhost:8080/graphql",
});

const authLink = setContext((_, { headers }) => {
  const token = getToken();
  return {
    headers: {
      ...headers,
      Authorization: token ? `Bearer ${token}` : "",
    },
  };
});

const errorLink = onError(({ graphQLErrors, networkError }) => {
  if (graphQLErrors?.length) {
    for (const err of graphQLErrors) {
      const message = err?.message || "GraphQL error";

      // Basic auth handling (adjust if backend uses different message)
      if (
        message.toLowerCase().includes("jwt") ||
        message.toLowerCase().includes("unauthorized") ||
        message.toLowerCase().includes("forbidden")
      ) {
        toast.error("Session expired. Please log in again.");
        logout();
        return;
      }

      toast.error(message);
    }
  }

  if (networkError) {
    toast.error("Network error. Please check the server and try again.");
  }
});

const client = new ApolloClient({
  link: from([errorLink, authLink, httpLink]),
  cache: new InMemoryCache(),
});

export default client;