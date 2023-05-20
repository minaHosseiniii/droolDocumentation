package org.example.model;

public class PrivateAccount extends Account {
    private Customer owner;

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("-------------Private account --------------");
        buffer.append(super.toString());
        if (this.owner != null) {
            buffer.append(this.owner.toString());
        }
        buffer.append("------ Private account end --------");
        return buffer.toString();
    }
}
