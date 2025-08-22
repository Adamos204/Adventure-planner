import { useEffect, useState } from "react"
import { useForm } from "react-hook-form"
import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { Link, useNavigate, useParams } from "react-router-dom"
import { createGear, getGear, updateGear } from "../components/GearsFetch.tsx"

const schema = z.object({
    name: z.string().min(2).max(20),
    quantity: z.coerce.number().int().min(1),
    packed: z.boolean().optional(),
})

type GearFormData = z.infer<typeof schema>

export default function GearForm() {
    const { adventureId, gearId } = useParams()
    const isEdit = Boolean(gearId)
    const navigate = useNavigate()
    const [loading, setLoading] = useState(isEdit)

    const { register, handleSubmit, reset, formState: { errors, isSubmitting } } =
        useForm<GearFormData>({ resolver: zodResolver(schema) as never })

    useEffect(() => {
        if (!isEdit || !gearId) return
        getGear(gearId).then((g) => {
            reset({ name: g.name, quantity: g.quantity, packed: g.packed })
        }).finally(() => setLoading(false))
    }, [isEdit, gearId, reset])

    async function onSubmit(values: GearFormData) {
        if (!adventureId) return
        if (isEdit && gearId) {
            await updateGear(gearId, { name: values.name, quantity: values.quantity, packed: values.packed })
        } else {
            await createGear(adventureId, { name: values.name, quantity: values.quantity })
        }
        navigate(`/adventures/${adventureId}/gear`)
    }

    const input = "w-full rounded-xl border px-3 py-2 outline-none transition focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
    const err = "text-xs text-red-600"
    if (loading) return <div className="max-w-md mx-auto p-6">Loading…</div>

    return (
        <div className="max-w-md mx-auto p-6 space-y-4">
            <h1 className="text-2xl font-bold">{isEdit ? "Edit Gear Item" : "Add Gear Item"}</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div>
                    <label className="text-sm text-gray-700">Name</label>
                    <input {...register("name")} className={`${input} mt-1 ${errors.name ? "border-red-500" : "border-gray-300"}`} />
                    {errors.name && <p className={err}>{errors.name.message}</p>}
                </div>

                <div>
                    <label className="text-sm text-gray-700">Quantity</label>
                    <input type="number" min={1} {...register("quantity")}
                           className={`${input} mt-1 ${errors.quantity ? "border-red-500" : "border-gray-300"}`} />
                    {errors.quantity && <p className={err}>{errors.quantity.message}</p>}
                </div>

                {isEdit && (
                    <label className="inline-flex items-center gap-2">
                        <input type="checkbox" {...register("packed")} />
                        <span className="text-sm">Packed</span>
                    </label>
                )}

                <button
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full rounded-xl bg-blue-600 px-4 py-2 text-white font-medium shadow hover:opacity-90 transition disabled:opacity-60"
                >
                    {isEdit ? "Save changes" : "Add Item"}
                </button>
            </form>

            <Link to={`/adventures/${adventureId}/gear`} className="block text-center mt-4 text-blue-600 hover:underline">
                ← Back to gear list
            </Link>
        </div>
    )
}