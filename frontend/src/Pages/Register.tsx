import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate } from "react-router-dom";

const schema = z.object({
    name: z.string().min(1, "Name is required"),
    email: z.string().email("Enter a valid email"),
    password: z.string().min(8, "At least 8 characters"),
    confirmPassword: z.string().min(1, "Please confirm your password"),
}).refine(data => data.password === data.confirmPassword, {
    message: "Passwords must match",
    path: ["confirmPassword"],
});

type RegisterFormData = z.infer<typeof schema>;

export default function Register() {
    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm<RegisterFormData>({
        resolver: zodResolver(schema),
    });

    function onSubmit(data: RegisterFormData) {
        console.log("REGISTER payload:", data);
        navigate("/dashboard");
    }

    return (
        <div className="p-6 max-w-md mx-auto">
            <h1 className="text-3xl font-bold mb-4">Register</h1>

            <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
                {/* Name */}
                <label className="flex flex-col gap-1">
                    <span className="text-sm">Name</span>
                    <input {...register("name")} placeholder="Jane Doe" className={`border rounded p-2 ${errors.name ? "border-red-500" : ""}`} />
                    {errors.name && <span className="text-xs text-red-600">{errors.name.message}</span>}
                </label>

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

                {/* Confirm Password */}
                <label className="flex flex-col gap-1">
                    <span className="text-sm">Confirm Password</span>
                    <input {...register("confirmPassword")} type="password" placeholder="••••••••" className={`border rounded p-2 ${errors.confirmPassword ? "border-red-500" : ""}`} />
                    {errors.confirmPassword && <span className="text-xs text-red-600">{errors.confirmPassword.message}</span>}
                </label>

                <button type="submit" className="rounded-xl shadow px-4 py-2 border">
                    Create account
                </button>
            </form>
        </div>
    );
}
