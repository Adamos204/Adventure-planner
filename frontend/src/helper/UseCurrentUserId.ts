import { useEffect, useState } from "react";
import { fetchMe } from "./auth.ts";

export function useCurrentUserId() {
    const [userId, setUserId] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        let mounted = true;
        (async () => {
            try {
                const me = await fetchMe();
                if (mounted) setUserId(me?.id ?? null);
            } finally {
                if (mounted) setLoading(false);
            }
        })();
        return () => { mounted = false; };
    }, []);

    return { userId, loading };
}
