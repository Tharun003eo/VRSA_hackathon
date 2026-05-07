import { NavLink, Outlet, useLocation, useNavigate } from "react-router-dom";
import {
  BookOpen,
  Users,
  GraduationCap,
  Handshake,
  Bookmark,
  CheckCircle2,
  LayoutDashboard,
  LogOut,
  Menu,
  Search,
} from "lucide-react";

import { cn } from "../lib/utils";
import { Button } from "../components/ui/button";
import { Input } from "../components/ui/input";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "../components/ui/dropdown-menu";
import { logout } from "../utils/auth";

const navItems = [
  { to: "/dashboard", label: "Dashboard", icon: LayoutDashboard },
  { to: "/books", label: "Books", icon: BookOpen },
  { to: "/users", label: "Users", icon: Users },
  { to: "/courses", label: "Courses", icon: GraduationCap },
  { to: "/lending", label: "Lending", icon: Handshake },
  { to: "/reservations", label: "Reservations", icon: Bookmark },
  { to: "/workflow", label: "Workflow", icon: CheckCircle2 },
];

export default function DashboardLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const currentPath = location.pathname;
  const currentLabel =
    navItems.find((n) => currentPath.startsWith(n.to))?.label || "Portal";

  return (
    <div className="min-h-screen bg-muted/30">
      <div className="grid lg:grid-cols-[280px_1fr]">
        <aside className="hidden lg:flex lg:flex-col border-r bg-background">
          <div className="h-16 px-6 flex items-center border-b">
            <div className="min-w-0">
              <div className="font-semibold tracking-tight leading-none">
                Smart Faculty
              </div>
              <div className="text-xs text-muted-foreground mt-1">
                Admin dashboard
              </div>
            </div>
          </div>
          <nav className="p-3 space-y-1 flex-1">
            {navItems.map((item) => {
              const Icon = item.icon;
              return (
                <NavLink
                  key={item.to}
                  to={item.to}
                  className={({ isActive }) =>
                    cn(
                      "group flex items-center gap-3 rounded-lg px-3 py-2 text-sm text-muted-foreground hover:bg-accent hover:text-accent-foreground",
                      isActive &&
                        "bg-accent text-accent-foreground font-medium shadow-sm"
                    )
                  }
                >
                  <Icon className="opacity-80 group-hover:opacity-100" />
                  {item.label}
                </NavLink>
              );
            })}
          </nav>
          <div className="p-4 border-t">
            <Button
              variant="outline"
              className="w-full justify-start"
              onClick={() => {
                logout();
                navigate("/", { replace: true });
              }}
            >
              <LogOut />
              Logout
            </Button>
          </div>
        </aside>

        <div className="min-w-0">
          <header className="h-16 bg-background/80 backdrop-blur border-b flex items-center justify-between px-4 lg:px-8 sticky top-0 z-10">
            <div className="flex items-center gap-3 min-w-0">
              <div className="lg:hidden">
                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <Button variant="outline" size="icon" aria-label="Menu">
                      <Menu />
                    </Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="start">
                    <DropdownMenuLabel>Navigate</DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    {navItems.map((item) => (
                      <DropdownMenuItem
                        key={item.to}
                        onClick={() => navigate(item.to)}
                      >
                        {item.label}
                      </DropdownMenuItem>
                    ))}
                    <DropdownMenuSeparator />
                    <DropdownMenuItem
                      onClick={() => {
                        logout();
                        navigate("/", { replace: true });
                      }}
                    >
                      Logout
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>

              <div className="min-w-0">
                <div className="text-sm font-medium truncate">{currentLabel}</div>
                <div className="text-xs text-muted-foreground truncate">
                  Academic Resource Management System
                </div>
              </div>
            </div>

            <div className="hidden sm:flex items-center gap-2">
              <div className="relative w-[280px] lg:w-[360px]">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground size-4" />
                <Input
                  className="pl-9 bg-muted/40"
                  placeholder="Search (coming soon)…"
                  aria-label="Search"
                  readOnly
                />
              </div>

              <Button
                variant="outline"
                onClick={() => {
                  logout();
                  navigate("/", { replace: true });
                }}
              >
                <LogOut />
                Logout
              </Button>
            </div>
          </header>

          <main className="p-4 lg:p-8">
            <Outlet />
          </main>
        </div>
      </div>
    </div>
  );
}

