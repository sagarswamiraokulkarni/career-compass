import React, { useState } from 'react';
import './AddTags.css';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {orderApi} from "../misc/OrderApi";
import {urlPaths} from "../../Constants";
import ConfirmationModal from './ConfirmationModal';

const AddTags = () => {
    const existingTags = JSON.parse(localStorage.getItem('allTags'))
    const [tags, setTags] = useState(existingTags);
    const [newTag, setNewTag] = useState('');
    const [addedTags, setAddedTags] = useState([]);
    const [deletedTags, setDeletedTags] = useState([]);
    const notify = (message) => toast(message);
    const [showConfirmationModal, setShowConfirmationModal] = useState(false);

    const handleAddTag = () => {
        if (newTag.trim() !== '' && !tags.includes(newTag.trim()) && !addedTags.includes(newTag.trim())) {
            // setTags([...tags, newTag.trim()]);
            setAddedTags([...addedTags, newTag.trim()]);
            setNewTag('');
        }else if(addedTags.includes(newTag.trim())){
            notify(`Tag: ${newTag.trim()} already in to be added list !..`)
        }else{
            notify(`Tag: ${newTag.trim()} was already added !..`)
        }
    };

    const handleRemoveTag = (tagToRemove,type) => {
        if(type==='existing') {
            setTags(tags.filter(tag => tag !== tagToRemove));
            setDeletedTags([...deletedTags,tagToRemove]);
        }else if(type==='new') {
            setAddedTags(addedTags.filter(tag => tag !== tagToRemove));
        }else if(type==='delete'){
            setTags([...tags,tagToRemove]);
            setDeletedTags(deletedTags.filter(tag => tag !== tagToRemove));
        }
    };

    // const handleConfirm =  () => {
    //     const storedUser = JSON.parse(localStorage.getItem('userDetails'));
    //     const userJson = JSON.parse(localStorage.getItem('user'));
    //     const allTags = JSON.parse(localStorage.getItem('allTags'));
    //     addedTags.map(async (tag) => {
    //         try {
    //             const response = await orderApi.postApiCall(userJson, urlPaths.CREATE_TAG, {name: tag, userId:storedUser.userId});
    //             console.log('API Response:', response);
    //         } catch (error) {
    //             console.error('Error fetching data:', error);
    //         }
    //     })
    //     console.log("begore")
    //     allTags.push(...addedTags);
    //     // allTags.push(addedTags);
    //     localStorage.setItem('allTags', JSON.stringify(allTags));
    //     console.log("after")
    //     console.log('Selected tags:', addedTags);
    // };

    const handleConfirm = () => {
        addedTags.length>0&&setShowConfirmationModal(true);
    };
    const confirmDelete = async () => {
        const storedUser = JSON.parse(localStorage.getItem('userDetails'));
        const userJson = JSON.parse(localStorage.getItem('user'));
        const allTags = JSON.parse(localStorage.getItem('allTags'));

        try {
            await Promise.all(addedTags.map(async (tag) => {
                const response = await orderApi.postApiCall(userJson, urlPaths.CREATE_TAG, {name: tag, userId:storedUser.userId});
                console.log('API Response:', response);
            }));

            allTags.push(...addedTags);
            localStorage.setItem('allTags', JSON.stringify(allTags));
            console.log('Selected tags:', addedTags);
            setAddedTags([]);
            setShowConfirmationModal(false);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    return (
        <div className="add-tags-background-container">
        <div className="add-tags-container">
            <ToastContainer/>
            <h2 className="add-tags-title">Add Tags</h2>
            <div className="tag-list">
                {tags.map((tag, index) => (
                    <span key={index} className="tag">
                        {tag}
                        {/*<button className="remove-tag-btn" onClick={() => handleRemoveTag(tag,"existing")}>x</button>*/}
                    </span>
                ))}
            </div>
            <div className="input-container">
                <input
                    type="text"
                    value={newTag}
                    onChange={(e) => setNewTag(e.target.value)}
                    placeholder="Add a new tag"
                    className="tag-input"
                />
                <button className="add-tag-btn" onClick={handleAddTag}>Add Tag</button>
            </div>
            {addedTags.length > 0 && (
                <div className="added-tags-container">
                    <h4>Tags to be Added:</h4>
                    <div className="added-tag-list">
                        {addedTags.map((tag, index) => (
                            <span key={index} className={`added-tag tag-${index}`}>
                    {tag}
                                <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag, "new")}>x</button>
                </span>
                        ))}
                    </div>
                </div>
            )}
            {deletedTags.length>0&&<div className="added-tags-container">
                <h4>Tags to be Deleted:</h4>
                <div className="added-tag-list">
                    {deletedTags.map((tag, index) => (
                        <span key={index} className={`added-tag tag-${index}`}>
                            {tag}
                            <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag,"delete")}>x</button>
                        </span>
                    ))}
                </div>
            </div>}
            <button className="confirm-btn" onClick={handleConfirm}>Confirm</button>
            <ConfirmationModal
                show={showConfirmationModal}
                onHide={() => setShowConfirmationModal(false)}
                onConfirm={confirmDelete}
                bodyContent={
                    <>
                        Are you sure you want to add this tags?:&nbsp;
                        {addedTags.map((tag, index) => (
                            <span key={index}>
                            {index > 0 && ", "}
                            {tag}
                            </span>
                        ))}
                    </>
                }
            />
        </div>
        </div>
    );
};

export default AddTags;