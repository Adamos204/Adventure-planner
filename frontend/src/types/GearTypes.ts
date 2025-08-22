export type GearItem = {
    id: number
    name: string
    quantity: number
    packed: boolean
    adventureId?: number
}

export type CreateGearPayload = {
    name: string
    quantity: number
}

export type UpdateGearPayload = Partial<CreateGearPayload> & { packed?: boolean }
