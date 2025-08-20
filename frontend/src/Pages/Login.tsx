import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate, Link } from "react-router-dom";

const schema = z.object({
    email: z.string().email("Enter a valid email"),
    password: z.string().min(8, "At least 8 characters"),
});

type LoginFormData = z.infer<typeof schema>;

export default function Login() {
    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm<LoginFormData>({
        resolver: zodResolver(schema),
    });

    function onSubmit(data: LoginFormData) {
        console.log("LOGIN payload:", data);
        navigate("/dashboard");
    }

    return (
        <div className="p-6 max-w-md mx-auto">
            <h1 className="text-3xl font-bold mb-4">Login</h1>

            <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
                {/* Email */}
                <label className="flex flex-col gap-1">
                    <span className="text-sm">Email</span>
                    <input {...register("email")} type="email" placeholder="jane@example.com" className={`border rounded p-2 ${errors.email ? "border-red-500" : ""}`} />
                    {errors.email && <span className="text-xs text-red-600">{errors.email.message}</span>}
                </label>

                {/* Password */}
                <label className="flex flex-col gap-1">
                    <span className="text-sm">Password</span>
                    <input {...register("password")} type="password" placeholder="••••••••" className={`border rounded p-2 ${errors.password ? "border-red-500" : ""}`} />
                    {errors.password && <span className="text-xs text-red-600">{errors.password.message}</span>}
                </label>

                <button type="submit" className="rounded-xl shadow px-4 py-2 border">
                    Sign in
                </button>
            </form>

            <p className="mt-4 text-sm text-center">
                Don’t have an account? <Link to="/register" className="underline">Register</Link>
            </p>
        </div>
    );
}
