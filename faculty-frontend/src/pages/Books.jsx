import { useState } from "react";

import { useLazyQuery } from "@apollo/client/react";

import { SEARCH_BOOKS } from "../graphql/queries/bookQueries";

import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card";
import { Input } from "../components/ui/input";
import { Button } from "../components/ui/button";

const Books = () => {

  const [keyword, setKeyword] = useState("");

  const [searchBooks, { loading, data, error }] =
    useLazyQuery(SEARCH_BOOKS);

  const handleSearch = () => {

    searchBooks({
      variables: {
        keyword
      }
    });
  };

  return (
    <div className="space-y-6">
      <div>
        <div className="text-2xl font-semibold tracking-tight">Books</div>
        <div className="text-sm text-muted-foreground mt-1">
          Search and manage book records.
        </div>
      </div>

      <Card>
        <CardHeader className="flex-row items-center justify-between space-y-0">
          <CardTitle className="text-base">Search</CardTitle>
        </CardHeader>
        <CardContent className="flex flex-col sm:flex-row gap-2">
          <Input
            placeholder="Search by title, ISBN, keyword…"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <Button onClick={handleSearch} disabled={!keyword || loading}>
            {loading ? "Searching..." : "Search"}
          </Button>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle className="text-base">Results</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          {error && (
            <div className="text-sm text-destructive">
              Failed to load books. Please try again.
            </div>
          )}

          {!loading && !data && (
            <div className="text-sm text-muted-foreground">
              Search to see matching books.
            </div>
          )}

          {!loading && data?.searchBooks?.length === 0 && (
            <div className="text-sm text-muted-foreground">
              No books found for “{keyword}”.
            </div>
          )}

          {data?.searchBooks?.map((book) => (
            <div
              key={book.id}
              className="rounded-lg border bg-background p-4"
            >
              <div className="flex items-start justify-between gap-4">
                <div>
                  <div className="font-medium">{book.title}</div>
                  <div className="text-xs text-muted-foreground mt-1">
                    ISBN: <span className="font-mono">{book.isbn}</span> ·
                    Version: <span className="font-mono">{book.version}</span> ·
                    Status: <span className="font-mono">{book.state}</span>
                  </div>
                </div>
              </div>
              {book.description && (
                <div className="text-sm text-muted-foreground mt-3">
                  {book.description}
                </div>
              )}
            </div>
          ))}
        </CardContent>
      </Card>
    </div>
  );
};

export default Books;