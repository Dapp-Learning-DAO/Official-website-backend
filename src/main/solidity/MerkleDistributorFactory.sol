pragma solidity ^0.8.0;

import "./MerkleDistributor.sol";

contract MerkleDistributorFactory {
    // Keep track of all created distributors
    MerkleDistributor[] public distributors;

    event DistributorCreated(address indexed distributorAddress);

    function createDistributor(address token, bytes32 merkleRoot) public {
        MerkleDistributor distributor = new MerkleDistributor(token, merkleRoot);
        distributors.push(distributor);
        emit DistributorCreated(address(distributor));
    }

    function getDistributor(uint index) public view returns (MerkleDistributor) {
        require(index < distributors.length, "Index out of bounds");
        return distributors[index];
    }

    function getDistributorsCount() public view returns (uint) {
        return distributors.length;
    }
}