import { useState } from "react";

import { useMutation } from "@apollo/client/react";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";

import { LOGIN_USER } from "../graphql/mutations/authMutations";

import { Button } from "../components/ui/button";
import { Input } from "../components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card";
import { setToken } from "../utils/auth";

const Login = () => {

  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const navigate = useNavigate();

  const [login, { loading, error }] =
    useMutation(LOGIN_USER);

  const handleChange = (e) => {

    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    if (!formData.email || !formData.password) {
      toast.error("Please enter email and password.");
      return;
    }

    try {

      const response = await login({
        variables: {
          email: formData.email,
          password: formData.password
        }
      });

      const token = response.data.login;

      setToken(token);

      toast.success("Login successful");
      navigate("/books", { replace: true });

      console.log("JWT Token:", token);

    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="min-h-screen bg-muted/40 flex items-center justify-center p-6">
      <div className="w-full max-w-md">
        <div className="mb-6 text-center">
          <div className="text-2xl font-semibold tracking-tight">
            Smart Faculty Portal
          </div>
          <div className="text-sm text-muted-foreground mt-1">
            Sign in to manage books and academic resources
          </div>
        </div>

        <Card>
          <CardHeader>
            <CardTitle>Login</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-3">
              <div className="space-y-1">
                <div className="text-sm font-medium">Email</div>
                <Input
                  type="email"
                  name="email"
                  placeholder="faculty@university.edu"
                  value={formData.email}
                  onChange={handleChange}
                  autoComplete="email"
                />
              </div>

              <div className="space-y-1">
                <div className="text-sm font-medium">Password</div>
                <Input
                  type="password"
                  name="password"
                  placeholder="••••••••"
                  value={formData.password}
                  onChange={handleChange}
                  autoComplete="current-password"
                />
              </div>

              <Button className="w-full" type="submit" disabled={loading}>
                {loading ? "Logging in..." : "Login"}
              </Button>

              {error && (
                <div className="text-sm text-destructive">
                  Invalid credentials. Please try again.
                </div>
              )}
            </form>
          </CardContent>
        </Card>

        <div className="mt-6 text-center text-xs text-muted-foreground">
          Backend: <span className="font-mono">http://localhost:8080/graphql</span>
        </div>
      </div>
    </div>
  );
};

export default Login;