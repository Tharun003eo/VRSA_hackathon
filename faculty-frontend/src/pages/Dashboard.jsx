import {
  BookOpen,
  Users,
  GraduationCap,
  Handshake,
  ArrowRight,
  CheckCircle2,
  Clock,
} from "lucide-react";

import { Card, CardContent, CardHeader, CardTitle } from "../components/ui/card";
import { Button } from "../components/ui/button";
import { Badge } from "../components/ui/badge";
import { Separator } from "../components/ui/separator";

const stats = [
  { label: "Books", value: "—", icon: BookOpen },
  { label: "Users", value: "—", icon: Users },
  { label: "Courses", value: "—", icon: GraduationCap },
  { label: "Issued", value: "—", icon: Handshake },
];

export default function Dashboard() {
  return (
    <div className="space-y-6">
      <div className="flex items-start justify-between gap-4">
        <div>
          <div className="text-2xl font-semibold tracking-tight">Dashboard</div>
          <div className="text-sm text-muted-foreground mt-1">
            Quick overview and common actions.
          </div>
        </div>
        <div className="flex gap-2">
          <Button
            variant="secondary"
            onClick={() => (window.location.href = "/books")}
          >
            Browse books <ArrowRight />
          </Button>
        </div>
      </div>

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4">
        {stats.map((s) => {
          const Icon = s.icon;
          return (
            <Card key={s.label} className="relative overflow-hidden">
              <CardHeader className="flex-row items-center justify-between space-y-0">
                <CardTitle className="text-sm font-medium">{s.label}</CardTitle>
                <div className="h-9 w-9 rounded-lg bg-muted flex items-center justify-center">
                  <Icon className="text-muted-foreground" />
                </div>
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-semibold">{s.value}</div>
                <div className="text-xs text-muted-foreground mt-1">
                  Will auto-populate once DB has data
                </div>
              </CardContent>
              <div className="pointer-events-none absolute -right-10 -top-10 h-28 w-28 rounded-full bg-primary/5" />
            </Card>
          );
        })}
      </div>

      <div className="grid gap-4 lg:grid-cols-3">
        <Card className="lg:col-span-2">
          <CardHeader className="flex-row items-center justify-between space-y-0">
            <CardTitle>Quick actions</CardTitle>
            <Badge variant="secondary">Starter</Badge>
          </CardHeader>
          <CardContent className="grid gap-2 sm:grid-cols-2">
            <Button
              variant="outline"
              className="justify-between"
              onClick={() => (window.location.href = "/books")}
            >
              Search books <ArrowRight />
            </Button>
            <Button
              variant="outline"
              className="justify-between"
              onClick={() => (window.location.href = "/users")}
            >
              View faculty/students <ArrowRight />
            </Button>
            <Button
              variant="outline"
              className="justify-between"
              onClick={() => (window.location.href = "/lending")}
            >
              Issue / return books <ArrowRight />
            </Button>
            <Button
              variant="outline"
              className="justify-between"
              onClick={() => (window.location.href = "/workflow")}
            >
              Review workflow <ArrowRight />
            </Button>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="flex items-center justify-between">
              <span>System status</span>
              <Badge variant="default">Online</Badge>
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-3">
            <div className="flex items-center gap-2 text-sm">
              <CheckCircle2 className="text-muted-foreground" />
              GraphQL connected via Apollo
            </div>
            <div className="flex items-center gap-2 text-sm">
              <CheckCircle2 className="text-muted-foreground" />
              JWT auth header attached
            </div>
            <Separator />
            <div className="flex items-center gap-2 text-sm text-muted-foreground">
              <Clock className="text-muted-foreground" />
              Populate DB to enable real counts
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

