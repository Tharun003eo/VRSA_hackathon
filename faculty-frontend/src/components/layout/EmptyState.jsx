import { Button } from "../ui/button";

export default function EmptyState({
  title,
  description,
  actionLabel,
  onAction,
  icon: Icon,
}) {
  return (
    <div className="rounded-xl border bg-background p-8">
      <div className="flex items-start gap-4">
        {Icon ? (
          <div className="h-10 w-10 rounded-lg bg-muted flex items-center justify-center">
            <Icon className="text-muted-foreground" />
          </div>
        ) : null}
        <div className="min-w-0">
          <div className="text-base font-semibold">{title}</div>
          {description ? (
            <div className="text-sm text-muted-foreground mt-1">
              {description}
            </div>
          ) : null}
          {actionLabel && onAction ? (
            <div className="mt-4">
              <Button variant="secondary" onClick={onAction}>
                {actionLabel}
              </Button>
            </div>
          ) : null}
        </div>
      </div>
    </div>
  );
}

