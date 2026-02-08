import { Quantity } from './Quantity.ts';

export class InsufficientQuantityError extends Error {
  constructor(
    public readonly available: Quantity,
    public readonly requested: Quantity
  ) {
    super(`Insufficient quantity. Available: ${available}, Requested: ${requested}`);
    this.name = 'InsufficientQuantityError';
  }
}

export class Item {
  private constructor(
    public readonly id: string,
    public readonly name: string,
    private currentQuantity: Quantity,
    public readonly initialQuantity: Quantity,
    public readonly expiryDate: Date | null,
    public readonly purchaseDate: Date
  ) {}

  static create = (params: {
    id: string;
    name: string;
    currentQuantity: Quantity;
    initialQuantity: Quantity;
    expiryDate: Date | null;
    purchaseDate: Date;
  }): Item => {
    return new Item(
      params.id, params.name, params.currentQuantity,
      params.initialQuantity, params.expiryDate, params.purchaseDate
    );
  };

  consume = (amount: Quantity): void => {
    if (!this.currentQuantity.canSubtract(amount)) {
      throw new InsufficientQuantityError(this.currentQuantity, amount);
    }
    this.currentQuantity = this.currentQuantity.subtract(amount);
  };

  hasEnough = (required: Quantity): boolean => {
    return this.currentQuantity.canSubtract(required);
  };

  getCurrentQuantity = (): Quantity => {
    return this.currentQuantity;
  };

  isFullyConsumed = (): boolean => {
    return this.currentQuantity.amount === 0;
  };
}
