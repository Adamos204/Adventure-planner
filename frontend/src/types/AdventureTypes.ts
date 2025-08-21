export type Adventure = {
    id: number
    name: string
    location: string
    description: string
    date: string
    notes?: string
    lengthInDays: number
    lengthInKm: number
    startLocation: string
    endLocation: string
    userId?: number
}

export type CreateAdventurePayload = Omit<Adventure, "id">
export type UpdateAdventurePayload = Partial<CreateAdventurePayload>