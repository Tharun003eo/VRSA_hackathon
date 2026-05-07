import { gql } from "@apollo/client/core";

export const SEARCH_BOOKS = gql`
  query SearchBooks($keyword: String!) {

    searchBooks(keyword: $keyword) {
      id
      title
      isbn
      version
      state
      description
    }
  }
`;