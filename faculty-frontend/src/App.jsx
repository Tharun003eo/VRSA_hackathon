import { Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Books from "./pages/Books";

import ProtectedRoute from "./components/ProtectedRoute";
import { Toaster } from "./components/ui/toaster";
import DashboardLayout from "./layouts/DashboardLayout";
import Dashboard from "./pages/Dashboard";
import EmptyState from "./components/layout/EmptyState";
import {
  GraduationCap,
  Handshake,
  Users,
  Bookmark,
  CheckCircle2,
} from "lucide-react";

function App() {

  return (
    <>
      <Routes>
        <Route path="/" element={<Login />} />

        <Route
          element={
            <ProtectedRoute>
              <DashboardLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/books" element={<Books />} />
          <Route
            path="/users"
            element={
              <EmptyState
                icon={Users}
                title="Users module"
                description="Register users, browse faculty and students, and view profiles."
                actionLabel="Go to dashboard"
                onAction={() => (window.location.href = "/dashboard")}
              />
            }
          />
          <Route
            path="/courses"
            element={
              <EmptyState
                icon={GraduationCap}
                title="Courses module"
                description="Create courses, assign books, and manage materials."
                actionLabel="Go to dashboard"
                onAction={() => (window.location.href = "/dashboard")}
              />
            }
          />
          <Route
            path="/lending"
            element={
              <EmptyState
                icon={Handshake}
                title="Lending module"
                description="Issue, return, renew, and track due dates."
                actionLabel="Go to dashboard"
                onAction={() => (window.location.href = "/dashboard")}
              />
            }
          />
          <Route
            path="/reservations"
            element={
              <EmptyState
                icon={Bookmark}
                title="Reservations module"
                description="Reserve books, view queues, and manage cancellations."
                actionLabel="Go to dashboard"
                onAction={() => (window.location.href = "/dashboard")}
              />
            }
          />
          <Route
            path="/workflow"
            element={
              <EmptyState
                icon={CheckCircle2}
                title="Workflow module"
                description="Submit books for review and handle approvals/rejections."
                actionLabel="Go to dashboard"
                onAction={() => (window.location.href = "/dashboard")}
              />
            }
          />
        </Route>
      </Routes>
      <Toaster />
    </>
  );
}

export default App;